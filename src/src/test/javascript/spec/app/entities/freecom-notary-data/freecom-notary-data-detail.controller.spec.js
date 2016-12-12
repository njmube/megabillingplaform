'use strict';

describe('Controller Tests', function() {

    describe('Freecom_notary_data Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_notary_data, MockFreecom_public_notaries, MockC_pn_federal_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_notary_data = jasmine.createSpy('MockFreecom_notary_data');
            MockFreecom_public_notaries = jasmine.createSpy('MockFreecom_public_notaries');
            MockC_pn_federal_entity = jasmine.createSpy('MockC_pn_federal_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_notary_data': MockFreecom_notary_data,
                'Freecom_public_notaries': MockFreecom_public_notaries,
                'C_pn_federal_entity': MockC_pn_federal_entity
            };
            createController = function() {
                $injector.get('$controller')("Freecom_notary_dataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_notary_dataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
