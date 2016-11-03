'use strict';

describe('Controller Tests', function() {

    describe('Com_used_vehicle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_used_vehicle, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_used_vehicle = jasmine.createSpy('MockCom_used_vehicle');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_used_vehicle': MockCom_used_vehicle,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_used_vehicleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_used_vehicleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
