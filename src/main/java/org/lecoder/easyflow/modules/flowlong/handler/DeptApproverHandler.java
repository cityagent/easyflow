package org.lecoder.easyflow.modules.flowlong.handler;

import com.aizuda.bpm.engine.TaskActorProvider;
import com.aizuda.bpm.engine.core.Execution;
import com.aizuda.bpm.engine.entity.FlwTaskActor;
import com.aizuda.bpm.engine.model.NodeModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */
@Component
public class DeptApproverHandler implements TaskActorProvider {
    @Override
    public List<FlwTaskActor> getTaskActors(NodeModel nodeModel, Execution execution) {
        return null;
    }

    @Override
    public Integer getActorType(NodeModel nodeModel) {
        return 0;
    }
}
