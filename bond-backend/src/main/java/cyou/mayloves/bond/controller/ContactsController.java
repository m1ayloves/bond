package cyou.mayloves.bond.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cyou.mayloves.bond.entity.Contact;
import cyou.mayloves.bond.handler.CellStyleHandler;
import cyou.mayloves.bond.model.ContactExcelModel;
import cyou.mayloves.bond.service.ContactService;
import cyou.mayloves.bond.utils.JsonUtils;
import cyou.mayloves.bond.utils.Result;
import cyou.mayloves.bond.utils.UserIdContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contacts")
public class ContactsController {

    private final ContactService contactService;

    @GetMapping("/list")
    public Result<IPage<Contact>> list(
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int size,
            Contact contact
    ) {
        Integer userId = UserIdContextHolder.getUserId();
        LambdaQueryWrapper<Contact> wrapper = Wrappers.lambdaQuery(Contact.class)
                .eq(Contact::getUserId, userId)
                .like(StringUtils.hasText(contact.getName()), Contact::getName, contact.getName())
                .like(StringUtils.hasText(contact.getTag()), Contact::getTag, contact.getTag())
                .eq(StringUtils.hasText(contact.getSex()), Contact::getSex, contact.getSex())
                .eq(contact.getBirthday() != null, Contact::getBirthday, contact.getBirthday())
                .like(StringUtils.hasText(contact.getPhone()), Contact::getPhone, contact.getPhone())
                .like(StringUtils.hasText(contact.getEmail()), Contact::getEmail, contact.getEmail())
                .like(StringUtils.hasText(contact.getAddress()), Contact::getAddress, contact.getAddress())
                .like(StringUtils.hasText(contact.getDescription()), Contact::getDescription, contact.getDescription());
        IPage<Contact> page = contactService.page(Page.of(current, size), wrapper);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<Contact> getInfo(@PathVariable Integer id) {
        Integer userId = UserIdContextHolder.getUserId();
        Contact contact = contactService.getOne(Wrappers.lambdaQuery(Contact.class)
                        .eq(Contact::getNo, id)
                        .eq(Contact::getUserId, userId),
                false
        );
        return contact != null ? Result.success(contact) : Result.error("Contact not found or access denied.");
    }

    @PostMapping
    public Result<Void> save(@RequestBody Contact contact) {
        contact.setUserId(UserIdContextHolder.getUserId());
        contactService.save(contact);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody Contact contact) {
        Integer userId = UserIdContextHolder.getUserId();
        boolean success = contactService.update(
                Wrappers.lambdaUpdate(Contact.class)
                        .set(Contact::getName, contact.getName())
                        .set(Contact::getTag, contact.getTag())
                        .set(Contact::getSex, contact.getSex())
                        .set(Contact::getBirthday, contact.getBirthday())
                        .set(Contact::getPhone, contact.getPhone())
                        .set(Contact::getEmail, contact.getEmail())
                        .set(Contact::getAddress, contact.getAddress())
                        .set(Contact::getDescription, contact.getDescription())
                        .eq(Contact::getUserId, userId)
                        .eq(Contact::getNo, contact.getNo())
        );
        return success ? Result.success() : Result.error("Contact not found or access denied.");
    }

    @DeleteMapping("/{id}")
    public Result<String> remove(@PathVariable Integer id) {
        Integer userId = UserIdContextHolder.getUserId();
        boolean success = contactService.remove(
                Wrappers.lambdaQuery(Contact.class)
                        .eq(Contact::getNo, id)
                        .eq(Contact::getUserId, userId)
        );
        return success ? Result.success() : Result.error("Contact not found or access denied.");
    }


    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() throws Exception {
        Integer userId = UserIdContextHolder.getUserId();
        LambdaQueryWrapper<Contact> wrapper = Wrappers.lambdaQuery(Contact.class)
                .eq(Contact::getUserId, userId);
        List<Contact> contacts = contactService.list(wrapper);
        List<ContactExcelModel> excelModels = BeanUtil.copyToList(contacts, ContactExcelModel.class);
        // 重新排列序号，重新生成性别
        for (int i = 0; i < excelModels.size(); i++) {
            excelModels.get(i).setNo(i + 1);
            if (excelModels.get(i).getSex() != null) {
                excelModels.get(i).setSex(excelModels.get(i).getSex().equals("M") ? "男" : "女");
            }
        }
        String fileName = "contacts.xlsx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, ContactExcelModel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
                .registerWriteHandler(new CellStyleHandler()) // 自定义样式
                .sheet("contacts")
                .doWrite(excelModels);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @GetMapping("/export/json")
    public ResponseEntity<byte[]> exportJson() {
        Integer userId = UserIdContextHolder.getUserId();
        LambdaQueryWrapper<Contact> wrapper = Wrappers.lambdaQuery(Contact.class)
                .eq(Contact::getUserId, userId);
        List<Contact> contacts = contactService.list(wrapper);
        // 重新排列序号，重新生成性别
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).setNo(i + 1);
            if (contacts.get(i).getSex() != null) {
                contacts.get(i).setSex(contacts.get(i).getSex().equals("M") ? "男" : "女");
            }
        }
        String json = JsonUtils.toPrettyJsonString(contacts);
        String fileName = "contacts.json";
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}