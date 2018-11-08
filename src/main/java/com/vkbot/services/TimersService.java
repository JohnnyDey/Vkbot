package com.vkbot.services;

import com.vkbot.entity.TimerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TimersService implements Serializable {

    private Logger logger = LoggerFactory.getLogger(TimersService.class);

    @Resource
    private TimerService timerService;

    @Inject
    private Event<TimerRemind> timerRemindEvent;

    @Timeout
    public void timeout(Timer timer) {
        timerRemindEvent.fire(new TimerRemind((TimerId) timer.getInfo()));
    }

    public void startTimer(Date date, Integer to, String msg) {
        logger.info("Timer for " + to + " at "  + date + " is setting: " + msg);
        timerService.createSingleActionTimer(date, new TimerConfig(new TimerId(to, msg), true));
    }

    public boolean stopTimer(TimerId timerId) {
        Optional<Timer> first = findTimer(timerId);
        first.ifPresent(javax.ejb.Timer::cancel);
        return first.isPresent();
    }

    private Optional<Timer> findTimer(TimerId timerId) {
        return timerService.getAllTimers().stream().filter(timer -> timer.getInfo().equals(timerId)).findFirst();
    }

    public List<Timer> findTimers(Integer id) {
        return timerService.getAllTimers().stream().filter(timer -> ((TimerId)timer.getInfo()).getId().equals(id)).collect(Collectors.toList());
    }

    public void stopTimers() {
        timerService.getTimers().forEach(javax.ejb.Timer::cancel);
    }

    public Collection<Timer> getTimers(){
        return timerService.getTimers();
    }
}
