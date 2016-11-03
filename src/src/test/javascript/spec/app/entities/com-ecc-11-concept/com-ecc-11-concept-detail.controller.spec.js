'use strict';

describe('Controller Tests', function() {

    describe('Com_ecc_11_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_ecc_11_concept, MockCom_ecc_11, MockCom_product_key, MockC_tar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_ecc_11_concept = jasmine.createSpy('MockCom_ecc_11_concept');
            MockCom_ecc_11 = jasmine.createSpy('MockCom_ecc_11');
            MockCom_product_key = jasmine.createSpy('MockCom_product_key');
            MockC_tar = jasmine.createSpy('MockC_tar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_ecc_11_concept': MockCom_ecc_11_concept,
                'Com_ecc_11': MockCom_ecc_11,
                'Com_product_key': MockCom_product_key,
                'C_tar': MockC_tar
            };
            createController = function() {
                $injector.get('$controller')("Com_ecc_11_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_ecc_11_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
