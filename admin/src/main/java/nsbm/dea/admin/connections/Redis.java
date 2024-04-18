package nsbm.dea.admin.connections;

import nsbm.dea.admin.config.Env;
import redis.clients.jedis.JedisPool;

public class Redis {
  public static JedisPool getPool() {
    JedisPool jedis = new JedisPool(
        Env.getRedisURL());
    return jedis;
  }
}
