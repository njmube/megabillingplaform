(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientDialogController', Taxpayer_clientDialogController);

    Taxpayer_clientDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', '$q', 'entity', 'taxpayer_account_entity', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'Taxpayer_client'];

    function Taxpayer_clientDialogController ($timeout, $scope, $uibModalInstance, $q, entity, taxpayer_account_entity, C_country, C_state, C_municipality, C_colony, C_zip_code, Taxpayer_client) {
        var vm = this;

        vm.taxpayer_client = entity;
        vm.taxpayer_account = taxpayer_account_entity;

        vm.client_address = { street: null, no_int: null, no_ext: null, location: null, intersection: null, reference: null, id: null, c_country: {id: 151, name: "México", abrev: "MEX"}};
        vm.c_countries = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalities = null;
        vm.c_colonies = null;

        if(vm.taxpayer_client.id != null) {
            vm.taxpayer_account = vm.taxpayer_client.taxpayer_account;
            vm.client_address = vm.taxpayer_client.client_address;

            if (vm.client_address.c_country != null) {
                onChangeC_country();
            }

            if (vm.client_address.c_state != null) {
                onChangeC_state()
            }

            if (vm.client_address.c_municipality != null) {
                onChangeC_municipality()
            }
        }

        vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;

        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function onChangeC_country () {
            var countryId = vm.client_address.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.client_address.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalities = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.client_address.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonies = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.client_address.c_colony.c_zip_code.id}, function(result) {
                vm.client_address.c_zip_code = result;
            });
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.taxpayer_client.taxpayer_account = vm.taxpayer_account;
            vm.taxpayer_client.client_address = vm.client_address;
            if (vm.taxpayer_client.id !== null) {
                Taxpayer_client.update(vm.taxpayer_client, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_client.save(vm.taxpayer_client, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_clientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
