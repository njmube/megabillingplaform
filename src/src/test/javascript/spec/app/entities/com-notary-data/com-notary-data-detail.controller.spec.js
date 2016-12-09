'use strict';

describe('Controller Tests', function() {

    describe('Com_notary_data Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_notary_data, MockCom_public_notaries, MockPublic_notaries_federal_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_notary_data = jasmine.createSpy('MockCom_notary_data');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            MockPublic_notaries_federal_entity = jasmine.createSpy('MockPublic_notaries_federal_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_notary_data': MockCom_notary_data,
                'Com_public_notaries': MockCom_public_notaries,
                'Public_notaries_federal_entity': MockPublic_notaries_federal_entity
            };
            createController = function() {
                $injector.get('$controller')("Com_notary_dataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_notary_dataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
