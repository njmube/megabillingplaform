'use strict';

describe('Controller Tests', function() {

    describe('Free_part_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_part_concept, MockFree_concept, MockMeasure_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_part_concept = jasmine.createSpy('MockFree_part_concept');
            MockFree_concept = jasmine.createSpy('MockFree_concept');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_part_concept': MockFree_part_concept,
                'Free_concept': MockFree_concept,
                'Measure_unit': MockMeasure_unit
            };
            createController = function() {
                $injector.get('$controller')("Free_part_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_part_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
