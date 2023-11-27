package cn.pepedd.service.impl;

import cn.pepedd.entity.pojo.Favorite;
import cn.pepedd.mapper.FavoriteMapper;
import cn.pepedd.service.IFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2023/12/1
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {
}
