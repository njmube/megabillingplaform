(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptController', ConceptController);

    ConceptController.$inject = ['$scope', '$state', 'taxpayer_account_entity', 'Taxpayer_account', 'Measure_unit', '$uibModal', 'Concept', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function ConceptController ($scope, $state, taxpayer_account_entity, Taxpayer_account, Measure_unit, $uibModal, Concept, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_accounts = Taxpayer_account.query({
            page: 0,
            size: 10
        });

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

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        loadAll();

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

            Concept.query({
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
                vm.concepts = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.addConcept = addConcept;
        vm.viewConcept = viewConcept;
        vm.editConcept = editConcept;
        vm.deleteConcept = deleteConcept;

        function addConcept(){
            $uibModal.open({
                templateUrl: 'app/entities/concept/concept-dialog.html',
                controller: 'ConceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            no_identification: null,
                            quantity: (0).toFixed(6),
                            description: null,
                            unit_value: (0).toFixed(6),
                            predial_number: null,
                            discount: (0).toFixed(2),
                            amount: null,
                            id: null
                        };
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function() {
                loadAll();
            });
        }

        function viewConcept(id){}
        function editConcept(id){
            $uibModal.open({
                templateUrl: 'app/entities/concept/concept-dialog.html',
                controller: 'ConceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function() {
                        return Concept.get({id : id}).$promise;
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function() {
                loadAll();
            });
        }
        function deleteConcept(id){
            $uibModal.open({
                templateUrl: 'app/entities/concept/concept-delete-dialog.html',
                controller: 'ConceptDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: function() {
                        return Concept.get({id : id}).$promise;
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.go($state.$current, {
                id: vm.taxpayer_account.id,
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
