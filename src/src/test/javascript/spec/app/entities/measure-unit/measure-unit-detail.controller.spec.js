'use strict';

describe('Controller Tests', function() {

    describe('Measure_unit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMeasure_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Measure_unit': MockMeasure_unit
            };
            createController = function() {
                $injector.get('$controller')("Measure_unitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:measure_unitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
