package cn.pepedd.service.impl;

import cn.pepedd.entity.pojo.Tag;
import cn.pepedd.mapper.TagMapper;
import cn.pepedd.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/12/1
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
}
