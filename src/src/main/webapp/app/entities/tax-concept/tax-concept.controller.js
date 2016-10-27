(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptController', Tax_conceptController);

    Tax_conceptController.$inject = ['$uibModal', '$state', 'taxpayer_account_entity', 'Taxpayer_account', 'Tax_types', 'Tax_concept', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function Tax_conceptController ($uibModal, $state, taxpayer_account_entity, Taxpayer_account, Tax_types, Tax_concept, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_accounts = Taxpayer_account.query({ page: 0, size: 10});

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

        vm.tax_type = null;
        vm.rate = null;
        vm.concept = null;
        vm.tax_types = [];
        Tax_types.query({filtername: " "}, function(data){
            var tax_types = data;
            var i;
            for(i = 0; tax_types.length; i++){
                if(tax_types[i].id != 2){
                    vm.tax_types.push(tax_types[i]);
                }
            }
        });
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

        function loadAll () {
            var tax_type = " ";
            var rate = -1;
            var concept = " ";
            var conceptid = 0;

            if(vm.tax_type != null) {
                tax_type = vm.tax_type.name;
            }

            if(vm.rate != null && vm.rate != "") {
                rate = vm.rate;
            }

            if(vm.concept != null && vm.concept != "") {
                concept = vm.concept;
            }

            Tax_concept.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                taxpayeraccount: vm.taxpayer_account.id,
                tax_type: tax_type,
                rate: rate,
                concept: concept,
                conceptid: conceptid
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
                vm.tax_concepts = data;
                vm.page = pagingParams.page;
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

        vm.addTaxConcept = addTaxConcept;
        vm.editTaxConcept = editTaxConcept;
        vm.deleteTaxConcept = deleteTaxConcept;

        function addTaxConcept(){
            $uibModal.open({
                templateUrl: 'app/entities/tax-concept/tax-concept-dialog.html',
                controller: 'Tax_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            rate: (1).toFixed(2),
                            id: null
                        };
                    },
                    taxpayer_account_entity: function() {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }

        function editTaxConcept(id){
            $uibModal.open({
                templateUrl: 'app/entities/tax-concept/tax-concept-dialog.html',
                controller: 'Tax_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function() {
                        return Tax_concept.get({id : id});
                    },
                    taxpayer_account_entity: function() {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }

        function deleteTaxConcept(id){
            $uibModal.open({
                templateUrl: 'app/entities/tax-concept/tax-concept-delete-dialog.html',
                controller: 'Tax_conceptDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: function() {
                        return Tax_concept.get({id : id});
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }

    }
})();
