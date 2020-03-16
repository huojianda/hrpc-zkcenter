package com.hrpc.discovery;

import java.util.List;

/**
 * @author huoji
 */
public interface LoadBalanceStrategy {

    String selectHost(List<String> repos);
}
