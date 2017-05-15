package com.lhx.effect.createanddestroy;

public class LHJProviderImpl implements Provider {
    @Override
    public Service newService() {
        return new LHJServiceImpl();
    }
}
