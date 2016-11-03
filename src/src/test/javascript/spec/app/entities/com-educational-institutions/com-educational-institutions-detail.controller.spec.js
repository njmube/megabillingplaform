'use strict';

describe('Controller Tests', function() {

    describe('Com_educational_institutions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_educational_institutions, MockCfdi, MockC_school_level;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_educational_institutions = jasmine.createSpy('MockCom_educational_institutions');
            MockCfdi = jasmine.createSpy('MockCfdi');
            MockC_school_level = jasmine.createSpy('MockC_school_level');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_educational_institutions': MockCom_educational_institutions,
                'Cfdi': MockCfdi,
                'C_school_level': MockC_school_level
            };
            createController = function() {
                $injector.get('$controller')("Com_educational_institutionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_educational_institutionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
