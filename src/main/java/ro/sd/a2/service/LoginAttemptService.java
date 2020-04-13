package ro.sd.a2.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    /**
     * constructor
     */
    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) { return 0; }
        });
    }

    /**
     * @param key - the ip address of the user who attempted a login
     *            - invalidates it - resets the attempt count
     */
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    /**
     * @param key - the ip address of the user who attempted a login
     *            - increments failed attempts count
     *            - loads into cache
     */
    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    /**
     * @param key - the ip address of the user who attempted a login
     * @return - returns the number of attempts already registered on that ip address
     */
    public int getAttemots(String key){
        int n = 0;
        try {
            n = attemptsCache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return n;
    }

    /**
     * @param key - the ip address of the user who attempted a login
     * @return true/false if there were more than 3 failed attempts to login on that ip
     */
    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}