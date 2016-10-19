(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_conceptController', Taxpayer_conceptController);

    Taxpayer_conceptController.$inject = ['$scope', '$state', 'taxpayer_account_entity', 'Taxpayer_account', 'Measure_unit', 'Taxpayer_concept', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', '$uibModal', 'Price_concept', 'Discount_concept', 'Measure_unit_concept'];

    function Taxpayer_conceptController ($scope, $state, taxpayer_account_entity, Taxpayer_account, Measure_unit, Taxpayer_concept, ParseLinks, AlertService, pagingParams, paginationConstants, $uibModal, Price_concept, Discount_concept, Measure_unit_concept) {
        var vm = this;

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_accounts = Taxpayer_account.query({page: 0, size: 10});

        vm.onChangeTaxPayerAccount = onChangeTaxPayerAccount;

        function onChangeTaxPayerAccount(){
            if(vm.taxpayer_account == null) {
                vm.taxpayer_account = {id: 0};
                loadAll();
                vm.taxpayer_account = null;
            }
            else {
                loadAll();
            }
        }

        vm.no_identification = null;
        vm.description = null;
        vm.measure_unit = null;
        vm.measure_units = Measure_unit.query({pg: -1, filtername:" "});
        vm.unit_value = null;

        vm.search = search;

        function search(){
            loadAll();
        }

        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAll();

        vm.price_concepts = [];
        vm.discount_concepts = [];
        vm.measure_unit_concepts = [];

        function loadAll () {
            var no_identification = " ";
            var description = " ";
            var measure_unit = " ";
            var unit_value = -1;

            if(vm.no_identification != null && vm.no_identification != "") {
                no_identification = vm.no_identification;
            }

            if(vm.description != null && vm.description != "") {
                description = vm.description;
            }

            if(vm.measure_unit != null) {
                measure_unit = vm.measure_unit.name;
            }

            if(vm.unit_value != null && vm.unit_value != "") {
                unit_value = vm.unit_value;
            }

            Taxpayer_concept.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                taxpayeraccount: vm.taxpayer_account.id,
                no_identification: no_identification,
                description: description,
                measure_unit: measure_unit,
                unit_value: unit_value
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.taxpayer_concepts = data;
                vm.page = pagingParams.page;

                vm.price_concepts = [];
                vm.discount_concepts = [];
                vm.measure_unit_concepts = [];

                var i;
                for(i = 0; i < vm.taxpayer_concepts.length; i++){
                    var prices = Price_concept.query({taxpayerconcept: vm.taxpayer_concepts[i].id});
                    var discounts = Discount_concept.query({taxpayerconcept: vm.taxpayer_concepts[i].id});
                    var measure_units = Measure_unit_concept.query({taxpayerconcept: vm.taxpayer_concepts[i].id});
                    vm.price_concepts.push(prices);
                    vm.discount_concepts.push(discounts);
                    vm.measure_unit_concepts.push(measure_units);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                id: vm.taxpayer_account.id,
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        vm.addTaxpayerConcept = addTaxpayerConcept;
        vm.editTaxpayerConcept = editTaxpayerConcept;
        vm.deleteTaxpayerConcept = deleteTaxpayerConcept;

        function addTaxpayerConcept(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-dialog.html',
                controller: 'Taxpayer_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            no_identification: null,
                            description: null,
                            predial_number: null,
                            id: null
                        };
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function() {
                loadAll ();
            });
        }

        function editTaxpayerConcept(id){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-dialog.html',
                controller: 'Taxpayer_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return Taxpayer_concept.get({id : id}).$promise;
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function() {
                loadAll ();
            });
        }

        function deleteTaxpayerConcept(id){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-delete-dialog.html',
                controller: 'Taxpayer_conceptDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: function () {
                        return Taxpayer_concept.get({id : id}).$promise;
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function() {
                loadAll ();
            });
        }

    }
})();
