'use strict';

describe('Controller Tests', function() {

    describe('Com_desc_estate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_desc_estate, MockCom_public_notaries, MockC_colony, MockC_municipality, MockC_state, MockC_country, MockC_zip_code, MockC_type_estate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_desc_estate = jasmine.createSpy('MockCom_desc_estate');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            MockC_type_estate = jasmine.createSpy('MockC_type_estate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_desc_estate': MockCom_desc_estate,
                'Com_public_notaries': MockCom_public_notaries,
                'C_colony': MockC_colony,
                'C_municipality': MockC_municipality,
                'C_state': MockC_state,
                'C_country': MockC_country,
                'C_zip_code': MockC_zip_code,
                'C_type_estate': MockC_type_estate
            };
            createController = function() {
                $injector.get('$controller')("Com_desc_estateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_desc_estateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
