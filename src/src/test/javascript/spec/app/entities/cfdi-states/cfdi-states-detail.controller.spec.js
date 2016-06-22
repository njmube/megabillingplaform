'use strict';

describe('Controller Tests', function() {

    describe('Cfdi_states Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCfdi_states;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCfdi_states = jasmine.createSpy('MockCfdi_states');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cfdi_states': MockCfdi_states
            };
            createController = function() {
                $injector.get('$controller')("Cfdi_statesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:cfdi_statesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
