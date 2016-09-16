'use strict';

describe('Controller Tests', function() {

    describe('Concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockConcept, MockTaxpayer_account, MockMeasure_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockConcept = jasmine.createSpy('MockConcept');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            MockMeasure_unit = jasmine.createSpy('MockMeasure_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Concept': MockConcept,
                'Taxpayer_account': MockTaxpayer_account,
                'Measure_unit': MockMeasure_unit
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
