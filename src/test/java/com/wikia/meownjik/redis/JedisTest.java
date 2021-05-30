package com.wikia.meownjik.redis;

import org.testng.Assert;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    @Test
    public void verifyCaching() {
        String cacheName = "testList";
        var jedis = new Jedis();
        jedis.lpush(cacheName, "Test", "Test2", "ZTest5");
        String res = jedis.lpop(cacheName);
        System.out.println(res);
        Assert.assertEquals(res, "ZTest5", "Error - incorrect pop result");
    }

    @Test
    public void verifyCachingIntegration() {
        //Cacher.cache();
        System.out.println(Cacher.pop());
        System.out.println(Cacher.pop());
    }
}
