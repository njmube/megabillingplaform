'use strict';

describe('Controller Tests', function() {

    describe('Public_notaries_federal_entity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPublic_notaries_federal_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPublic_notaries_federal_entity = jasmine.createSpy('MockPublic_notaries_federal_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Public_notaries_federal_entity': MockPublic_notaries_federal_entity
            };
            createController = function() {
                $injector.get('$controller')("Public_notaries_federal_entityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:public_notaries_federal_entityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
