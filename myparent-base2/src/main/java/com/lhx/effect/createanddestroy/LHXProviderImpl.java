package com.lhx.effect.createanddestroy;

public class LHXProviderImpl implements Provider {
    @Override
    public Service newService() {
        return new LHXServiceImpl();
    }
}
