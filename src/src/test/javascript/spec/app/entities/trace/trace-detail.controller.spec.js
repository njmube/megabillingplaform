'use strict';

describe('Controller Tests', function() {

    describe('Trace Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTrace, MockAudit_event_type, MockC_state_event;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTrace = jasmine.createSpy('MockTrace');
            MockAudit_event_type = jasmine.createSpy('MockAudit_event_type');
            MockC_state_event = jasmine.createSpy('MockC_state_event');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Trace': MockTrace,
                'Audit_event_type': MockAudit_event_type,
                'C_state_event': MockC_state_event
            };
            createController = function() {
                $injector.get('$controller')("TraceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:traceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
