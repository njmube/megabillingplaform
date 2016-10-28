'use strict';

describe('Controller Tests', function() {

    describe('Customs_info_part Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCustoms_info_part, MockPart_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCustoms_info_part = jasmine.createSpy('MockCustoms_info_part');
            MockPart_concept = jasmine.createSpy('MockPart_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Customs_info_part': MockCustoms_info_part,
                'Part_concept': MockPart_concept
            };
            createController = function() {
                $injector.get('$controller')("Customs_info_partDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:customs_info_partUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
