static {
try {
// 初始化配置管理
configBuilder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
.configure(new Parameters().properties().setFileName("config.properties"));
config = configBuilder.getConfiguration();

            // 初始化 Redis
//            redisClient = new Jedis(config.getString("redis.host", "localhost"),
//                    config.getInt("redis.port", 6379));
//            redisLock = new Jedis(config.getString("redis.host", "localhost"),
//                    config.getInt("redis.port", 6379));

            LOGGER.info("GlobalContext initialized successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize GlobalContext", e);
        }
    }