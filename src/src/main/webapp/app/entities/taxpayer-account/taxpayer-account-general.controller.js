(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountGeneralController', Taxpayer_accountGeneralController);

    Taxpayer_accountGeneralController.$inject = ['$scope', '$rootScope', '$uibModal', 'C_state', 'C_country', 'C_municipality', 'C_colony', 'C_zip_code', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountGeneralController($scope, $rootScope, $uibModal, C_state, C_country, C_municipality, C_colony, C_zip_code, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
        vm.c_colonys = null;
        vm.edit = null;
        vm.accuracys = [2,3,4,5,6];
        vm.showInfo = false;

        vm.clearInfor = clearInfor;
        vm.add = add;

        vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;
        vm.clicEdit = clicEdit;

        function clicEdit(){
            vm.edit = 'OK';
        }

        function clearInfor(){

            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-account/confirmation-delete-cert.html',
                controller: 'Confirmation_delete_certController',
                controllerAs: 'vm',
                backdrop: true,
                size: ''
            }).result.then(function(result) {
                    vm.taxpayer_account.taxpayer_certificate.number_certificate = null;
                    vm.taxpayer_account.taxpayer_certificate.pass_certificate = null;
                    vm.taxpayer_account.taxpayer_certificate.info_certificate = null;
                    vm.taxpayer_account.taxpayer_certificate.filecertificate = null;
                    vm.taxpayer_account.taxpayer_certificate.filecertificateContentType=null;
                    vm.certificatename=null;
                    vm.taxpayer_account.taxpayer_certificate.filekey=null;
                    vm.taxpayer_account.taxpayer_certificate.filekeyContentType=null;
                    vm.keyname=null;
                    vm.taxpayer_account.taxpayer_certificate.rfc_certificate = null;
                    vm.taxpayer_account.taxpayer_certificate.bussines_name_cert = null;
                    vm.taxpayer_account.taxpayer_certificate.date_created_cert = null;
                    vm.taxpayer_account.taxpayer_certificate.date_expiration_cert = null;

                    vm.showInfo = false;
                }, function() {
                });

        }

        function add(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-account/validate.html',
                controller: 'ValidateController',
                controllerAs: 'vm',
                backdrop: true,
                size: '',
                resolve: {
                    entity: function () {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function(result) {
                    vm.taxpayer_account = result.taxpayer_account;
                }, function() {
                });
        }

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function onChangeC_country () {
            var countryId = vm.taxpayer_account.tax_address.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.taxpayer_account.tax_address.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.taxpayer_account.tax_address.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.taxpayer_account.tax_address.c_colony.c_zip_code.id}, function(result) {
                vm.taxpayer_account.tax_address.c_zip_code = result;
            });
        }
    }
})();
