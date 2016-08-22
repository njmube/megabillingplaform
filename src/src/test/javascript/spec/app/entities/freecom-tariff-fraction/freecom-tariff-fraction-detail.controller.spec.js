'use strict';

describe('Controller Tests', function() {

    describe('Freecom_tariff_fraction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_tariff_fraction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_tariff_fraction = jasmine.createSpy('MockFreecom_tariff_fraction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_tariff_fraction': MockFreecom_tariff_fraction
            };
            createController = function() {
                $injector.get('$controller')("Freecom_tariff_fractionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_tariff_fractionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
