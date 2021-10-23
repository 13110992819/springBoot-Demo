package com.test.elastic.job2;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2021/4/12 21:54
 */
public class TestJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
                Thread.currentThread().getId(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem()));
    }
}

