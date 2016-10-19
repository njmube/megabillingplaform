'use strict';

describe('Controller Tests', function() {

    describe('Concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockConcept, MockMeasure_unit, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockConcept = jasmine.createSpy('MockConcept');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Concept': MockConcept,
                'Measure_unit': MockMeasure_unit,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("ConceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
