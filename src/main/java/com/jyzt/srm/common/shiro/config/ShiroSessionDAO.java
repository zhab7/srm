package com.jyzt.srm.common.shiro.config;

import com.jyzt.srm.common.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

@Slf4j
public class ShiroSessionDAO extends AbstractSessionDAO {
    // 用于放在redis里面的key
    private String PREFIX = "shiro:session:";

    private static ThreadLocal<Session> sessionInMem = new ThreadLocal<>();

    public ShiroSessionDAO() {

    }


    @Override
    protected Serializable doCreate(Session session) {
        checkSession(session);
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);

        String key = getKey(sessionId);
        long timeout = session.getTimeout();
        byte[] value = SerializeUtils.serialize(session);
        if (log.isDebugEnabled()) log.debug("-> create session {}", session);
//        shiroRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.warn("can find session by null session id");
            return null;
        }
        Session session;
        if ((session = sessionInMem.get()) != null) {
            return session;
        }

//        if (log.isDebugEnabled()) log.debug("-> read session from redis id {}", sessionId);
//        byte[] value = (byte[]) shiroRedisTemplate.opsForValue().get(getKey(sessionId));
//        if (ArrayUtils.isEmpty(value)) {
//            log.warn("-w-w-w-> can't find session from redis by id {}", sessionId);
//            return null;
//        }

//        session = (Session) SerializeUtils.deserialize(value);
        sessionInMem.set(session);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {

        checkSession(session);
//        String key = getKey(session.getId());
//        long timeout = session.getTimeout();
//        byte[] value = SerializeUtils.serialize(session);
//        if (log.isDebugEnabled()) log.debug("-> update session {}", session);
//        shiroRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
        sessionInMem.set(session);

    }

    @Override
    public void delete(Session session) {
        checkSession(session);
//        String key = getKey(session.getId());
//        shiroRedisTemplate.delete(key);
        clearSessionInMem();
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    private void checkSession(Session session) {
        if (session == null) {
            log.error("session is null!");
        }
    }
    private String getKey(Serializable sessionId) {
        return PREFIX + sessionId;
    }

    public void clearSessionInMem() {
        log.debug("-> remove session in memory ");
        sessionInMem.remove();
    }
}
