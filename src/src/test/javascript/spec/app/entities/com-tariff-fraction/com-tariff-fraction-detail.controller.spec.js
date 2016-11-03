'use strict';

describe('Controller Tests', function() {

    describe('Com_tariff_fraction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_tariff_fraction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_tariff_fraction = jasmine.createSpy('MockCom_tariff_fraction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_tariff_fraction': MockCom_tariff_fraction
            };
            createController = function() {
                $injector.get('$controller')("Com_tariff_fractionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_tariff_fractionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
