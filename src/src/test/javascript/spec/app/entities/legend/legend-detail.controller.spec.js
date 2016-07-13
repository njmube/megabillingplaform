'use strict';

describe('Controller Tests', function() {

    describe('Legend Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLegend, MockFreecom_taxlegends;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLegend = jasmine.createSpy('MockLegend');
            MockFreecom_taxlegends = jasmine.createSpy('MockFreecom_taxlegends');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Legend': MockLegend,
                'Freecom_taxlegends': MockFreecom_taxlegends
            };
            createController = function() {
                $injector.get('$controller')("LegendDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:legendUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
