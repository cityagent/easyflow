package org.lecoder.easyflow.modules.flowlong.config;

import com.aizuda.bpm.engine.core.FlowCreator;
import com.aizuda.bpm.engine.core.enums.TaskEventType;
import com.aizuda.bpm.engine.entity.FlwTask;
import com.aizuda.bpm.engine.entity.FlwTaskActor;
import com.aizuda.bpm.engine.listener.TaskListener;
import com.aizuda.bpm.engine.model.NodeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Administrator
 */
@Component
public class TestTaskListener implements TaskListener {
    @Override
    public boolean notify(TaskEventType eventType, Supplier<FlwTask> supplier, List<FlwTaskActor> taskActors, NodeModel nodeModel, FlowCreator flowCreator) {
        System.err.println("当前执行任务 = " + supplier.get().getTaskName() +
                " ，执行事件 = " + eventType.name() + "，创建人=" + flowCreator.getCreateBy());
        return true;
    }
}
