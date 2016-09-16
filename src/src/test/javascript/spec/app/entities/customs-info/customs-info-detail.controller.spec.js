'use strict';

describe('Controller Tests', function() {

    describe('Customs_info Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCustoms_info, MockConcept, MockPart_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCustoms_info = jasmine.createSpy('MockCustoms_info');
            MockConcept = jasmine.createSpy('MockConcept');
            MockPart_concept = jasmine.createSpy('MockPart_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Customs_info': MockCustoms_info,
                'Concept': MockConcept,
                'Part_concept': MockPart_concept
            };
            createController = function() {
                $injector.get('$controller')("Customs_infoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:customs_infoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
