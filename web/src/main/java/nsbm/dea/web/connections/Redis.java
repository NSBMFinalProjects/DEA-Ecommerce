package nsbm.dea.web.connections;

import nsbm.dea.web.config.Env;
import redis.clients.jedis.JedisPool;

public class Redis {
  public static JedisPool getPool() {
    JedisPool jedis = new JedisPool(
        Env.getRedisURL());
    return jedis;
  }
}
