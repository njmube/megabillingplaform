'use strict';

describe('Controller Tests', function() {

    describe('Free_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_concept, MockFree_cfdi, MockMeasure_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_concept = jasmine.createSpy('MockFree_concept');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_concept': MockFree_concept,
                'Free_cfdi': MockFree_cfdi,
                'Measure_unit': MockMeasure_unit
            };
            createController = function() {
                $injector.get('$controller')("Free_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
