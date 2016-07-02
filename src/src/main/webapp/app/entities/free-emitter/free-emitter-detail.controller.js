(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDetailController', Free_emitterDetailController);

    Free_emitterDetailController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'User'];

    function Free_emitterDetailController($scope, $stateParams, $uibModalInstance, $q, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, User) {
        var vm = this;
        vm.free_emitter = entity;
        vm.tax_regimes = Tax_regime.query();
        vm.c_countrys = C_country.query({pg:1});
        vm.c_states =  C_state.query({countryId: -1});
        vm.c_municipalitys = C_municipality.query({stateId: -1});
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.users = User.query();
        vm.onChangeCountry = onChangeCountry;
        vm.onChangeState = onChangeState;
        vm.load = function(id) {
            Free_emitter.get({id : id}, function(result) {
                vm.free_emitter = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_emitterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        function onChangeCountry () {
            var countryId = vm.free_emitter.c_country.id;
            C_state.query({countryId: countryId}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeState () {
            var stateId = vm.free_emitter.c_state.id;
            C_municipality.query({stateId: stateId}, function(result){
                vm.c_municipalitys = result;
            });
        }

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_emitter.id !== null) {
                Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
            } else {
                Free_emitter.save(vm.free_emitter, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.create_date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };


    }
})();
