package cyou.mayloves.bond.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyou.mayloves.bond.entity.Contact;
import cyou.mayloves.bond.mapper.ContactMapper;
import cyou.mayloves.bond.service.ContactService;
import org.springframework.stereotype.Service;


@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

}