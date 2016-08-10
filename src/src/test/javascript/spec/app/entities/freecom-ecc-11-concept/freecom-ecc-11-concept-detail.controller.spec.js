'use strict';

describe('Controller Tests', function() {

    describe('Freecom_ecc11_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_ecc11_concept, MockFreecom_ecc11, MockFreecom_product_key, MockC_tar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_ecc11_concept = jasmine.createSpy('MockFreecom_ecc11_concept');
            MockFreecom_ecc11 = jasmine.createSpy('MockFreecom_ecc11');
            MockFreecom_product_key = jasmine.createSpy('MockFreecom_product_key');
            MockC_tar = jasmine.createSpy('MockC_tar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_ecc11_concept': MockFreecom_ecc11_concept,
                'Freecom_ecc11': MockFreecom_ecc11,
                'Freecom_product_key': MockFreecom_product_key,
                'C_tar': MockC_tar
            };
            createController = function() {
                $injector.get('$controller')("Freecom_ecc11_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_ecc11_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
