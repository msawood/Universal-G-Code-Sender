/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.willwinder.ugs.platform.probe;

import com.willwinder.universalgcodesender.listeners.ControllerStateListener;
import com.willwinder.universalgcodesender.listeners.UGSEventListener;
import com.willwinder.universalgcodesender.model.BackendAPI;
import com.willwinder.universalgcodesender.model.Position;
import com.willwinder.universalgcodesender.model.UGSEvent;

/**
 *
 * @author wwinder
 */
public abstract class AbstractProbeService implements UGSEventListener, ControllerStateListener {
    protected final BackendAPI backend;
    public AbstractProbeService(BackendAPI backend) {
        this.backend = backend;
        this.backend.addUGSEventListener(this);
        this.backend.addControllerStateListener(this);
    }

    abstract void performInsideCornerProbe(ProbeContext initialContext) throws IllegalStateException;

    abstract void performOutsideCornerProbe(ProbeContext initialContext) throws IllegalStateException;

    protected static double retractDistance(double spacing) {
        return (spacing < 0) ? 1 : -1;
    }

    /**
     * Context passed into state machine for each transition.
     */
    public static class ProbeContext {
        public String errorMessage;
        public UGSEvent event;
        public final double probeDiameter;
        public final double xSpacing;
        public final double ySpacing;
        public final double feedRate;
        public final int wcsToUpdate;

        // Results
        public final Position startPosition;
        public Position probePosition1;
        public Position probePosition2;

        public ProbeContext(double diameter, Position start,
                double xSpacing, double ySpacing, double feedRate, int wcs) {
            this.probeDiameter = diameter;
            this.startPosition = start;
            this.xSpacing = xSpacing;
            this.ySpacing = ySpacing;
            this.feedRate = feedRate;
            this.wcsToUpdate = wcs;
        }
    }

    
}
