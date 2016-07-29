'use strict';

describe('Controller Tests', function() {

    describe('Freecom_foreign_tourist_passenger Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_foreign_tourist_passenger, MockC_transit_type, MockC_type_road, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_foreign_tourist_passenger = jasmine.createSpy('MockFreecom_foreign_tourist_passenger');
            MockC_transit_type = jasmine.createSpy('MockC_transit_type');
            MockC_type_road = jasmine.createSpy('MockC_type_road');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_foreign_tourist_passenger': MockFreecom_foreign_tourist_passenger,
                'C_transit_type': MockC_transit_type,
                'C_type_road': MockC_type_road,
                'Free_cfdi': MockFree_cfdi
            };
            createController = function() {
                $injector.get('$controller')("Freecom_foreign_tourist_passengerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_foreign_tourist_passengerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
