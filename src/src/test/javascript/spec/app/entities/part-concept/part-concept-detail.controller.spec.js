'use strict';

describe('Controller Tests', function() {

    describe('Part_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPart_concept, MockConcept, MockMeasure_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPart_concept = jasmine.createSpy('MockPart_concept');
            MockConcept = jasmine.createSpy('MockConcept');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Part_concept': MockPart_concept,
                'Concept': MockConcept,
                'Measure_unit': MockMeasure_unit
            };
            createController = function() {
                $injector.get('$controller')("Part_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:part_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
