'use strict';

describe('Controller Tests', function() {

    describe('Audit_event_type Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAudit_event_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAudit_event_type = jasmine.createSpy('MockAudit_event_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Audit_event_type': MockAudit_event_type
            };
            createController = function() {
                $injector.get('$controller')("Audit_event_typeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:audit_event_typeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
