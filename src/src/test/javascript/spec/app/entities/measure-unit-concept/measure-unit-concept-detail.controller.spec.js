'use strict';

describe('Controller Tests', function() {

    describe('Measure_unit_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMeasure_unit_concept, MockMeasure_unit, MockTaxpayer_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMeasure_unit_concept = jasmine.createSpy('MockMeasure_unit_concept');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            MockTaxpayer_concept = jasmine.createSpy('MockTaxpayer_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Measure_unit_concept': MockMeasure_unit_concept,
                'Measure_unit': MockMeasure_unit,
                'Taxpayer_concept': MockTaxpayer_concept
            };
            createController = function() {
                $injector.get('$controller')("Measure_unit_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:measure_unit_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
