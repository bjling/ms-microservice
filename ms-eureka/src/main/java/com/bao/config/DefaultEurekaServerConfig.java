package com.bao.config;

/**
 * Created by nannan on 2017/6/15.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.netflix.config.*;
import com.netflix.eureka.aws.AwsBindingStrategy;
import com.sun.istack.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.*;

@Singleton
public class DefaultEurekaServerConfig implements EurekaServerConfig {
    private static final String ARCHAIUS_DEPLOYMENT_ENVIRONMENT = "archaius.deployment.environment";
    private static final String TEST = "test";
    private static final String EUREKA_ENVIRONMENT = "eureka.environment";
    private static final Logger logger = LoggerFactory.getLogger(com.netflix.eureka.DefaultEurekaServerConfig.class);
    private static final DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
    private static final DynamicStringProperty EUREKA_PROPS_FILE = DynamicPropertyFactory.getInstance().getStringProperty("eureka.server.props", "eureka-server");
    private static final int TIME_TO_WAIT_FOR_REPLICATION = 30000;
    private String namespace = "eureka.";
    private final DynamicStringSetProperty rateLimiterPrivilegedClients;
    private final DynamicBooleanProperty rateLimiterEnabled;
    private final DynamicBooleanProperty rateLimiterThrottleStandardClients;
    private final DynamicIntProperty rateLimiterBurstSize;
    private final DynamicIntProperty rateLimiterRegistryFetchAverageRate;
    private final DynamicIntProperty rateLimiterFullFetchAverageRate;
    private final DynamicStringProperty listAutoScalingGroupsRoleName;

    public DefaultEurekaServerConfig() {
        this.rateLimiterPrivilegedClients = new DynamicStringSetProperty(this.namespace + "rateLimiter.privilegedClients", Collections.emptySet());
        this.rateLimiterEnabled = configInstance.getBooleanProperty(this.namespace + "rateLimiter.enabled", false);
        this.rateLimiterThrottleStandardClients = configInstance.getBooleanProperty(this.namespace + "rateLimiter.throttleStandardClients", false);
        this.rateLimiterBurstSize = configInstance.getIntProperty(this.namespace + "rateLimiter.burstSize", 10);
        this.rateLimiterRegistryFetchAverageRate = configInstance.getIntProperty(this.namespace + "rateLimiter.registryFetchAverageRate", 500);
        this.rateLimiterFullFetchAverageRate = configInstance.getIntProperty(this.namespace + "rateLimiter.fullFetchAverageRate", 100);
        this.listAutoScalingGroupsRoleName = configInstance.getStringProperty(this.namespace + "listAutoScalingGroupsRoleName", "ListAutoScalingGroups");
        this.init();
    }

    public DefaultEurekaServerConfig(String namespace) {
        this.rateLimiterPrivilegedClients = new DynamicStringSetProperty(this.namespace + "rateLimiter.privilegedClients", Collections.emptySet());
        this.rateLimiterEnabled = configInstance.getBooleanProperty(this.namespace + "rateLimiter.enabled", false);
        this.rateLimiterThrottleStandardClients = configInstance.getBooleanProperty(this.namespace + "rateLimiter.throttleStandardClients", false);
        this.rateLimiterBurstSize = configInstance.getIntProperty(this.namespace + "rateLimiter.burstSize", 10);
        this.rateLimiterRegistryFetchAverageRate = configInstance.getIntProperty(this.namespace + "rateLimiter.registryFetchAverageRate", 500);
        this.rateLimiterFullFetchAverageRate = configInstance.getIntProperty(this.namespace + "rateLimiter.fullFetchAverageRate", 100);
        this.listAutoScalingGroupsRoleName = configInstance.getStringProperty(this.namespace + "listAutoScalingGroupsRoleName", "ListAutoScalingGroups");
        this.namespace = namespace;
        this.init();
    }

    private void init() {
        String env = ConfigurationManager.getConfigInstance().getString("eureka.environment", "test");
        ConfigurationManager.getConfigInstance().setProperty("archaius.deployment.environment", env);
        String eurekaPropsFile = EUREKA_PROPS_FILE.get();

        try {
            ConfigurationManager.loadCascadedPropertiesFromResources(eurekaPropsFile);
        } catch (IOException var4) {
            logger.warn("Cannot find the properties specified : {}. This may be okay if there are other environment specific properties or the configuration is installed with a different mechanism.", eurekaPropsFile);
        }

    }

    public String getAWSAccessId() {
        String aWSAccessId = configInstance.getStringProperty(this.namespace + "awsAccessId", (String)null).get();
        return null != aWSAccessId?aWSAccessId.trim():null;
    }

    public String getAWSSecretKey() {
        String aWSSecretKey = configInstance.getStringProperty(this.namespace + "awsSecretKey", (String)null).get();
        return null != aWSSecretKey?aWSSecretKey.trim():null;
    }

    public int getEIPBindRebindRetries() {
        return configInstance.getIntProperty(this.namespace + "eipBindRebindRetries", 3).get();
    }

    public int getEIPBindingRetryIntervalMsWhenUnbound() {
        return configInstance.getIntProperty(this.namespace + "eipBindRebindRetryIntervalMsWhenUnbound", '\uea60').get();
    }

    public int getEIPBindingRetryIntervalMs() {
        return configInstance.getIntProperty(this.namespace + "eipBindRebindRetryIntervalMs", 300000).get();
    }

    public boolean shouldEnableSelfPreservation() {
        return configInstance.getBooleanProperty(this.namespace + "enableSelfPreservation", true).get();
    }

    public int getPeerEurekaNodesUpdateIntervalMs() {
        return configInstance.getIntProperty(this.namespace + "peerEurekaNodesUpdateIntervalMs", 600000).get();
    }

    public int getRenewalThresholdUpdateIntervalMs() {
        return configInstance.getIntProperty(this.namespace + "renewalThresholdUpdateIntervalMs", 900000).get();
    }

    public double getRenewalPercentThreshold() {
        return configInstance.getDoubleProperty(this.namespace + "renewalPercentThreshold", 0.85D).get();
    }

    public boolean shouldEnableReplicatedRequestCompression() {
        return configInstance.getBooleanProperty(this.namespace + "enableReplicatedRequestCompression", false).get();
    }

    public int getNumberOfReplicationRetries() {
        return configInstance.getIntProperty(this.namespace + "numberOfReplicationRetries", 5).get();
    }

    public int getPeerEurekaStatusRefreshTimeIntervalMs() {
        return configInstance.getIntProperty(this.namespace + "peerEurekaStatusRefreshTimeIntervalMs", 30000).get();
    }

    public int getWaitTimeInMsWhenSyncEmpty() {
        return configInstance.getIntProperty(this.namespace + "waitTimeInMsWhenSyncEmpty", 300000).get();
    }

    public int getPeerNodeConnectTimeoutMs() {
        return configInstance.getIntProperty(this.namespace + "peerNodeConnectTimeoutMs", 200).get();
    }

    public int getPeerNodeReadTimeoutMs() {
        return configInstance.getIntProperty(this.namespace + "peerNodeReadTimeoutMs", 200).get();
    }

    public int getPeerNodeTotalConnections() {
        return configInstance.getIntProperty(this.namespace + "peerNodeTotalConnections", 1000).get();
    }

    public int getPeerNodeTotalConnectionsPerHost() {
        return configInstance.getIntProperty(this.namespace + "peerNodeTotalConnectionsPerHost", 500).get();
    }

    public int getPeerNodeConnectionIdleTimeoutSeconds() {
        return configInstance.getIntProperty(this.namespace + "peerNodeConnectionIdleTimeoutSeconds", 30).get();
    }

    public long getRetentionTimeInMSInDeltaQueue() {
        return configInstance.getLongProperty(this.namespace + "retentionTimeInMSInDeltaQueue", 180000L).get();
    }

    public long getDeltaRetentionTimerIntervalInMs() {
        return configInstance.getLongProperty(this.namespace + "deltaRetentionTimerIntervalInMs", 30000L).get();
    }

    public long getEvictionIntervalTimerInMs() {
        return configInstance.getLongProperty(this.namespace + "evictionIntervalTimerInMs", 60000L).get();
    }

    public int getASGQueryTimeoutMs() {
        return configInstance.getIntProperty(this.namespace + "asgQueryTimeoutMs", 300).get();
    }

    public long getASGUpdateIntervalMs() {
        return (long)configInstance.getIntProperty(this.namespace + "asgUpdateIntervalMs", 300000).get();
    }

    public long getASGCacheExpiryTimeoutMs() {
        return (long)configInstance.getIntProperty(this.namespace + "asgCacheExpiryTimeoutMs", 600000).get();
    }

    public long getResponseCacheAutoExpirationInSeconds() {
        return (long)configInstance.getIntProperty(this.namespace + "responseCacheAutoExpirationInSeconds", 180).get();
    }

    public long getResponseCacheUpdateIntervalMs() {
        return (long)configInstance.getIntProperty(this.namespace + "responseCacheUpdateIntervalMs", 30000).get();
    }

    public boolean shouldUseReadOnlyResponseCache() {
        return configInstance.getBooleanProperty(this.namespace + "shouldUseReadOnlyResponseCache", true).get();
    }

    public boolean shouldDisableDelta() {
        return configInstance.getBooleanProperty(this.namespace + "disableDelta", false).get();
    }

    public long getMaxIdleThreadInMinutesAgeForStatusReplication() {
        return configInstance.getLongProperty(this.namespace + "maxIdleThreadAgeInMinutesForStatusReplication", 10L).get();
    }

    public int getMinThreadsForStatusReplication() {
        return configInstance.getIntProperty(this.namespace + "minThreadsForStatusReplication", 1).get();
    }

    public int getMaxThreadsForStatusReplication() {
        return configInstance.getIntProperty(this.namespace + "maxThreadsForStatusReplication", 1).get();
    }

    public int getMaxElementsInStatusReplicationPool() {
        return configInstance.getIntProperty(this.namespace + "maxElementsInStatusReplicationPool", 10000).get();
    }

    public boolean shouldSyncWhenTimestampDiffers() {
        return configInstance.getBooleanProperty(this.namespace + "syncWhenTimestampDiffers", true).get();
    }

    public int getRegistrySyncRetries() {
        return configInstance.getIntProperty(this.namespace + "numberRegistrySyncRetries", 5).get();
    }

    public long getRegistrySyncRetryWaitMs() {
        return (long)configInstance.getIntProperty(this.namespace + "registrySyncRetryWaitMs", 30000).get();
    }

    public int getMaxElementsInPeerReplicationPool() {
        return configInstance.getIntProperty(this.namespace + "maxElementsInPeerReplicationPool", 10000).get();
    }

    public long getMaxIdleThreadAgeInMinutesForPeerReplication() {
        return (long)configInstance.getIntProperty(this.namespace + "maxIdleThreadAgeInMinutesForPeerReplication", 15).get();
    }

    public int getMinThreadsForPeerReplication() {
        return configInstance.getIntProperty(this.namespace + "minThreadsForPeerReplication", 5).get();
    }

    public int getMaxThreadsForPeerReplication() {
        return configInstance.getIntProperty(this.namespace + "maxThreadsForPeerReplication", 20).get();
    }

    public int getMaxTimeForReplication() {
        return configInstance.getIntProperty(this.namespace + "maxTimeForReplication", 30000).get();
    }

    public boolean shouldPrimeAwsReplicaConnections() {
        return configInstance.getBooleanProperty(this.namespace + "primeAwsReplicaConnections", true).get();
    }

    public boolean shouldDisableDeltaForRemoteRegions() {
        return configInstance.getBooleanProperty(this.namespace + "disableDeltaForRemoteRegions", false).get();
    }

    public int getRemoteRegionConnectTimeoutMs() {
        return configInstance.getIntProperty(this.namespace + "remoteRegionConnectTimeoutMs", 1000).get();
    }

    public int getRemoteRegionReadTimeoutMs() {
        return configInstance.getIntProperty(this.namespace + "remoteRegionReadTimeoutMs", 1000).get();
    }

    public int getRemoteRegionTotalConnections() {
        return configInstance.getIntProperty(this.namespace + "remoteRegionTotalConnections", 1000).get();
    }

    public int getRemoteRegionTotalConnectionsPerHost() {
        return configInstance.getIntProperty(this.namespace + "remoteRegionTotalConnectionsPerHost", 500).get();
    }

    public int getRemoteRegionConnectionIdleTimeoutSeconds() {
        return configInstance.getIntProperty(this.namespace + "remoteRegionConnectionIdleTimeoutSeconds", 30).get();
    }

    public boolean shouldGZipContentFromRemoteRegion() {
        return configInstance.getBooleanProperty(this.namespace + "remoteRegion.gzipContent", true).get();
    }

    public Map<String, String> getRemoteRegionUrlsWithName() {
        String propName = this.namespace + "remoteRegionUrlsWithName";
        String remoteRegionUrlWithNameString = configInstance.getStringProperty(propName, (String)null).get();
        if(null == remoteRegionUrlWithNameString) {
            return Collections.emptyMap();
        } else {
            String[] remoteRegionUrlWithNamePairs = remoteRegionUrlWithNameString.split(",");
            Map<String, String> toReturn = new HashMap(remoteRegionUrlWithNamePairs.length);
            String pairSplitChar = ";";
            String[] var6 = remoteRegionUrlWithNamePairs;
            int var7 = remoteRegionUrlWithNamePairs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String remoteRegionUrlWithNamePair = var6[var8];
                String[] pairSplit = remoteRegionUrlWithNamePair.split(";");
                if(pairSplit.length < 2) {
                    logger.error("Error reading eureka remote region urls from property {}. Invalid entry {} for remote region url. The entry must contain region name and url separated by a {}. Ignoring this entry.", new String[]{propName, remoteRegionUrlWithNamePair, ";"});
                } else {
                    String regionName = pairSplit[0];
                    String regionUrl = pairSplit[1];
                    if(pairSplit.length > 2) {
                        StringBuilder regionUrlAssembler = new StringBuilder();

                        for(int i = 1; i < pairSplit.length; ++i) {
                            if(regionUrlAssembler.length() != 0) {
                                regionUrlAssembler.append(";");
                            }

                            regionUrlAssembler.append(pairSplit[i]);
                        }

                        regionUrl = regionUrlAssembler.toString();
                    }

                    toReturn.put(regionName, regionUrl);
                }
            }

            return toReturn;
        }
    }

    public String[] getRemoteRegionUrls() {
        String remoteRegionUrlString = configInstance.getStringProperty(this.namespace + "remoteRegionUrls", (String)null).get();
        String[] remoteRegionUrl = null;
        if(remoteRegionUrlString != null) {
            remoteRegionUrl = remoteRegionUrlString.split(",");
        }

        return remoteRegionUrl;
    }

    @Nullable
    public Set<String> getRemoteRegionAppWhitelist(@Nullable String regionName) {
        if(null == regionName) {
            regionName = "global";
        } else {
            regionName = regionName.trim().toLowerCase();
        }

        DynamicStringProperty appWhiteListProp = configInstance.getStringProperty(this.namespace + "remoteRegion." + regionName + ".appWhiteList", (String)null);
        if(null != appWhiteListProp && null != appWhiteListProp.get()) {
            String appWhiteListStr = appWhiteListProp.get();
            String[] whitelistEntries = appWhiteListStr.split(",");
            return new HashSet(Arrays.asList(whitelistEntries));
        } else {
            return null;
        }
    }

    public int getRemoteRegionRegistryFetchInterval() {
        return configInstance.getIntProperty(this.namespace + "remoteRegion.registryFetchIntervalInSeconds", 30).get();
    }

    public int getRemoteRegionFetchThreadPoolSize() {
        return configInstance.getIntProperty(this.namespace + "remoteRegion.fetchThreadPoolSize", 20).get();
    }

    public String getRemoteRegionTrustStore() {
        return configInstance.getStringProperty(this.namespace + "remoteRegion.trustStoreFileName", "").get();
    }

    public String getRemoteRegionTrustStorePassword() {
        return configInstance.getStringProperty(this.namespace + "remoteRegion.trustStorePassword", "changeit").get();
    }

    public boolean disableTransparentFallbackToOtherRegion() {
        return configInstance.getBooleanProperty(this.namespace + "remoteRegion.disable.transparent.fallback", false).get();
    }

    public boolean shouldBatchReplication() {
        return configInstance.getBooleanProperty(this.namespace + "shouldBatchReplication", false).get();
    }

    public boolean shouldLogIdentityHeaders() {
        return configInstance.getBooleanProperty(this.namespace + "auth.shouldLogIdentityHeaders", true).get();
    }

    public String getJsonCodecName() {
        return configInstance.getStringProperty(this.namespace + "jsonCodecName", (String)null).get();
    }

    public String getXmlCodecName() {
        return configInstance.getStringProperty(this.namespace + "xmlCodecName", (String)null).get();
    }

    public boolean isRateLimiterEnabled() {
        return this.rateLimiterEnabled.get();
    }

    public boolean isRateLimiterThrottleStandardClients() {
        return this.rateLimiterThrottleStandardClients.get();
    }

    public Set<String> getRateLimiterPrivilegedClients() {
        return this.rateLimiterPrivilegedClients.get();
    }

    public int getRateLimiterBurstSize() {
        return this.rateLimiterBurstSize.get();
    }

    public int getRateLimiterRegistryFetchAverageRate() {
        return this.rateLimiterRegistryFetchAverageRate.get();
    }

    public int getRateLimiterFullFetchAverageRate() {
        return this.rateLimiterFullFetchAverageRate.get();
    }

    public String getListAutoScalingGroupsRoleName() {
        return this.listAutoScalingGroupsRoleName.get();
    }

    public int getRoute53BindRebindRetries() {
        return configInstance.getIntProperty(this.namespace + "route53BindRebindRetries", 3).get();
    }

    public int getRoute53BindingRetryIntervalMs() {
        return configInstance.getIntProperty(this.namespace + "route53BindRebindRetryIntervalMs", 300000).get();
    }

    public long getRoute53DomainTTL() {
        return configInstance.getLongProperty(this.namespace + "route53DomainTTL", 30L).get();
    }

    public AwsBindingStrategy getBindingStrategy() {
        return AwsBindingStrategy.valueOf(configInstance.getStringProperty(this.namespace + "awsBindingStrategy", AwsBindingStrategy.EIP.name()).get().toUpperCase());
    }

    public String getExperimental(String name) {
        return configInstance.getStringProperty(this.namespace + "experimental." + name, (String)null).get();
    }

    public int getHealthStatusMinNumberOfAvailablePeers() {
        return configInstance.getIntProperty(this.namespace + "minAvailableInstancesForPeerReplication", -1).get();
    }
}

