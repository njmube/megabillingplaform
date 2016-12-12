'use strict';

describe('Controller Tests', function() {

    describe('Freecom_desc_estate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_desc_estate, MockFreecom_public_notaries, MockC_colony, MockC_municipality, MockC_state, MockC_country, MockC_zip_code, MockC_type_estate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_desc_estate = jasmine.createSpy('MockFreecom_desc_estate');
            MockFreecom_public_notaries = jasmine.createSpy('MockFreecom_public_notaries');
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
                'Freecom_desc_estate': MockFreecom_desc_estate,
                'Freecom_public_notaries': MockFreecom_public_notaries,
                'C_colony': MockC_colony,
                'C_municipality': MockC_municipality,
                'C_state': MockC_state,
                'C_country': MockC_country,
                'C_zip_code': MockC_zip_code,
                'C_type_estate': MockC_type_estate
            };
            createController = function() {
                $injector.get('$controller')("Freecom_desc_estateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_desc_estateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
