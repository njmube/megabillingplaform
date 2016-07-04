(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterNewController', Free_emitterNewController);

    Free_emitterNewController.$inject = ['$scope', '$stateParams', '$q', 'entity', 'DataUtils', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'user'];

    function Free_emitterNewController ($scope, $stateParams, $q, entity, DataUtils, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, user) {
        var vm = this;

        vm.account = user;
		vm.free_emitter = entity;
        vm.tax_regimes = Tax_regime.query();
        vm.c_countrys = C_country.query({pg:1});
        vm.c_states = C_state.query({countryId:-1});
        vm.onChangeC_country = onChangeC_country;
        vm.c_municipalitys = C_municipality.query({stateId:-1});
        vm.onChangeC_state = onChangeC_state;
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();

		vm.load = function(id) {
			Free_emitter.get({id : id}, function(result) {
                vm.free_emitter = result;
            });
        };

        function onChangeC_country () {
			var countryId = vm.free_emitter.c_country.id;
            C_state.query({countryId: countryId}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.free_emitter.c_state.id;
            C_municipality.query({stateId: stateId}, function(result){
                vm.c_municipalitys = result;
            });
        }

		var onSaveSuccess = function (result) {
            vm.free_emitter =  result;
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

		vm.save = function () {
			vm.isSaving = true;
            if (vm.free_emitter.id !== null) {
                Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
            } else {
                vm.free_emitter.create_date = Date.now();
                vm.free_emitter.activated = true;
				vm.free_emitter.user = vm.account;
                Free_emitter.save(vm.free_emitter, onSaveSuccess, onSaveError);
            }
        };
    }
})();
