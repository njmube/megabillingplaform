'use strict';

describe('Controller Tests', function() {

    describe('Com_vehicle_customs_information Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_vehicle_customs_information, MockCom_used_vehicle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_vehicle_customs_information = jasmine.createSpy('MockCom_vehicle_customs_information');
            MockCom_used_vehicle = jasmine.createSpy('MockCom_used_vehicle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_vehicle_customs_information': MockCom_vehicle_customs_information,
                'Com_used_vehicle': MockCom_used_vehicle
            };
            createController = function() {
                $injector.get('$controller')("Com_vehicle_customs_informationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_vehicle_customs_informationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
