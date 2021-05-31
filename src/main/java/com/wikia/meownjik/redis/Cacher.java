package com.wikia.meownjik.redis;

import com.wikia.meownjik.jdbc.EcoNewsDao;
import com.wikia.meownjik.jdbc.EcoNewsEntity;
import redis.clients.jedis.Jedis;

public class Cacher {

    private Cacher() {}

    public static final String CACHE_NAME = "newsList";

    public static void cache() {
        var jedis = new Jedis();
        var news = new EcoNewsDao().selectAll();
        for (var n: news) {
            jedis.lpush(CACHE_NAME, n.toString());
        }
    }

    public static EcoNewsEntity pop() {
        var jedis = new Jedis();
        String resStr = jedis.lpop(CACHE_NAME);
        if (resStr == null) {
            cache();
            resStr = jedis.lpop(CACHE_NAME);
        }
        return EcoNewsEntity.parseEcoNewsString(resStr);
    }
}
