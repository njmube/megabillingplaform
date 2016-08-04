'use strict';

describe('Controller Tests', function() {

    describe('Freecom_vehicle_customs_information Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_vehicle_customs_information, MockFreecom_used_vehicle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_vehicle_customs_information = jasmine.createSpy('MockFreecom_vehicle_customs_information');
            MockFreecom_used_vehicle = jasmine.createSpy('MockFreecom_used_vehicle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_vehicle_customs_information': MockFreecom_vehicle_customs_information,
                'Freecom_used_vehicle': MockFreecom_used_vehicle
            };
            createController = function() {
                $injector.get('$controller')("Freecom_vehicle_customs_informationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_vehicle_customs_informationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
