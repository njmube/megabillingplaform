(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-beneficiary', {
            parent: 'entity',
            url: '/com-beneficiary?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_beneficiary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-beneficiary/com-beneficiaries.html',
                    controller: 'Com_beneficiaryController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_beneficiary');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-beneficiary-detail', {
            parent: 'entity',
            url: '/com-beneficiary/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_beneficiary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-beneficiary/com-beneficiary-detail.html',
                    controller: 'Com_beneficiaryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_beneficiary');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_beneficiary', function($stateParams, Com_beneficiary) {
                    return Com_beneficiary.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-beneficiary.new', {
            parent: 'com-beneficiary',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-beneficiary/com-beneficiary-dialog.html',
                    controller: 'Com_beneficiaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                receiver_bank: null,
                                name: null,
                                type_account: null,
                                account: null,
                                rfc: null,
                                concept: null,
                                iva: null,
                                payment_amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-beneficiary', null, { reload: true });
                }, function() {
                    $state.go('com-beneficiary');
                });
            }]
        })
        .state('com-beneficiary.edit', {
            parent: 'com-beneficiary',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-beneficiary/com-beneficiary-dialog.html',
                    controller: 'Com_beneficiaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_beneficiary', function(Com_beneficiary) {
                            return Com_beneficiary.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-beneficiary', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-beneficiary.delete', {
            parent: 'com-beneficiary',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-beneficiary/com-beneficiary-delete-dialog.html',
                    controller: 'Com_beneficiaryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_beneficiary', function(Com_beneficiary) {
                            return Com_beneficiary.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-beneficiary', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
