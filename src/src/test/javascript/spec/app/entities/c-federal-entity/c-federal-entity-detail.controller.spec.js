'use strict';

describe('Controller Tests', function() {

    describe('C_federal_entity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_federal_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_federal_entity = jasmine.createSpy('MockC_federal_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_federal_entity': MockC_federal_entity
            };
            createController = function() {
                $injector.get('$controller')("C_federal_entityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_federal_entityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
